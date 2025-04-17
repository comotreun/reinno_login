package com.example.demo.datasources;

import javax.sql.DataSource;

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//자바 설정 클래스파일로 설정
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.example.demo.web", sqlSessionFactoryRef = "messageSqlSessionFactory")
public class MessageDataSourceConfig extends DataSourceConfig {
	//참조한 파일의 값을 가져오는 어노테이션
	@Value("${message.datasource.driver-class-name}")
	private String driverClassName; // 데이터베이스 드라이버 클래스 이름을 가져옵니다.

	@Value("${message.datasource.url}")
	private String jdbcUrl; // 데이터베이스 연결 URL을 가져옵니다.

	@Value("${message.datasource.user}")
	private String user; // 데이터베이스 사용자 이름을 가져옵니다.

	@Value("${message.datasource.password}")
	private String password; // 데이터베이스 비밀번호를 가져옵니다.

	@Value("${message.datasource.pool-name}")
	private String poolName; // 데이터베이스 커넥션 풀 이름을 가져옵니다.

	@Value("${message.datasource.max-pool-size}")
	private int maxPoolSize; // 데이터베이스 커넥션 풀의 최대 크기를 가져옵니다.

	/**
	 * 데이터베이스 연결을 위한 HikariCP DataSource 설정
	 *
	 * @author Hyun Jun-ho
	 * @return HikariCP로 설정된 DataSource
	 */
	@Bean(name = "messageDataSource")
	@Primary
	public DataSource messageDataSource() {
	    HikariConfig hikariConfig = new HikariConfig(); // HikariCP를 사용하여 데이터 소스를 구성하고 반환합니다.
	    hikariConfig.setDriverClassName(driverClassName); // 드라이버 클래스 이름 설정
	    hikariConfig.setJdbcUrl(jdbcUrl); // JDBC URL 설정
	    hikariConfig.setUsername(user); // 암호화된 사용자 이름을 복호화하여 설정
	    hikariConfig.setPassword(password); // 암호화된 비밀번호를 복호화하여 설정
	    hikariConfig.setPoolName(poolName); // 커넥션 풀 이름 설정
	    hikariConfig.setMaximumPoolSize(maxPoolSize); // 최대 커넥션 풀 크기 설정
	    hikariConfig.setTransactionIsolation("TRANSACTION_READ_UNCOMMITTED"); // 트랜잭션 격리 수준 설정 (UNCOMMITTED)
	    
	    return new HikariDataSource(hikariConfig); // HikariDataSource 인스턴스를 반환
	}

	/**
	 * MyBatis용 SqlSessionFactory 설정
	 *
	 * @author Hyun Jun-ho
	 * @param DataSource dataSource | 데이터베이스에 연결할 데이터 소스
	 * @return 설정된 SqlSessionFactory 객체
	 */
	@Bean(name = "messageSqlSessionFactory")
	@Primary
	public SqlSessionFactory messageSqlSessionFactory(@Qualifier("messageDataSource") DataSource dataSource) throws Exception {
	    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	    sessionFactory.setDataSource(dataSource); // 데이터 소스를 설정
	    sessionFactory.setConfiguration(messageMybatisConfig()); // MyBatis 설정을 적용
	    sessionFactory.setTypeAliasesPackage("com.example.demo.dto"); // DTO 클래스가 있는 패키지 설정
	    sessionFactory.setMapperLocations(resolver.getResources("classpath*:/mapper/message/*.xml")); // 매퍼 XML 파일 경로 설정 
	    
	    return sessionFactory.getObject(); // SqlSessionFactory 인스턴스를 반환
	}

	/**
	 * SqlSessionTemplate 생성
	 *
	 * @author Hyun Jun-ho
	 * @param SqlSessionFactory sessionFactory | 설정된 SqlSessionFactory 객체
	 * @return 기본 ExecutorType을 사용하는 SqlSessionTemplate
	 */
	@Bean(name = "messageSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate messageSqlSessionTemplate(@Qualifier("messageSqlSessionFactory") SqlSessionFactory sessionFactory) throws Exception {
	    SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sessionFactory); // SqlSessionTemplate 생성
	    
	    return sqlSessionTemplate; // SqlSessionTemplate 인스턴스를 반환
	}

	/**
	 * 배치 모드(BATCH)용 SqlSessionTemplate 생성
	 *
	 * @author Hyun Jun-ho
	 * @param SqlSessionFactory sessionFactory | 설정된 SqlSessionFactory 객체
	 * @return 배치용 SqlSessionTemplate
	 */
	@Bean(name = "messageSqlSessionTemplteBatch")
	public SqlSessionTemplate messageSqlSessionTemplateBatch(@Qualifier("messageSqlSessionFactory") SqlSessionFactory sessionFactory) {
	    SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sessionFactory, ExecutorType.BATCH); // 배치 모드로 SqlSessionTemplate 생성
	    
	    return sqlSessionTemplate; // SqlSessionTemplate 인스턴스를 반환
	}

	/**
	 * 트랜잭션 매니저 생성
	 *
	 * @author Hyun Jun-ho
	 * @return 설정된 DataSourceTransactionManager 객체
	 */
	@Bean(name = "messageTransactionManager") // 철자 수정
	@Primary
	public DataSourceTransactionManager messageDataSourceTransactionManager() throws Exception {
	    DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(messageDataSource()); // 트랜잭션 매니저 생성
	    
	    return dataSourceTransactionManager; // 트랜잭션 매니저 인스턴스를 반환
	}

	/**
	 * MyBatis 설정 구성
	 *
	 * @author Hyun Jun-ho
	 * @return 설정된 MyBatis Configuration 객체
	 */
	@Bean(name = "messageMybatisConfig")
	@Primary
	public org.apache.ibatis.session.Configuration messageMybatisConfig() {
	    org.apache.ibatis.session.Configuration mybatisConfig = new org.apache.ibatis.session.Configuration();
	    mybatisConfig.setDefaultExecutorType(ExecutorType.SIMPLE); // 기본 실행 타입을 SIMPLE로 설정
	    mybatisConfig.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL); // 자동 매핑 동작을 PARTIAL로 설정
	    mybatisConfig.setJdbcTypeForNull(JdbcType.NULL); // null 값을 위한 JDBC 타입 설정
	    mybatisConfig.setUseGeneratedKeys(true); // 자동 생성된 키 사용 설정 프라이머리키 가져올 수 있게
	    mybatisConfig.setLazyLoadingEnabled(false); // 지연 로딩 비활성화
	    mybatisConfig.setUseColumnLabel(true); // 컬럼 라벨 사용 설정
	    mybatisConfig.setMultipleResultSetsEnabled(true); // 여러 개의 결과 세트 사용 설정
	    mybatisConfig.setSafeRowBoundsEnabled(true); // 안전한 RowBounds 사용 설정
	    mybatisConfig.setMapUnderscoreToCamelCase(true); // 언더스코어를 CamelCase로 매핑 설정 
	    mybatisConfig.setSafeResultHandlerEnabled(false); // 안전한 결과 핸들러 사용 비활성화
	    
	    return mybatisConfig; // MyBatis 설정 인스턴스를 반환
	}
}
