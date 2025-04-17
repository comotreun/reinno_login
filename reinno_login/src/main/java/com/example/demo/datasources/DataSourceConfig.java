package com.example.demo.datasources;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//롤백커밋 담당 
@EnableTransactionManagement
//공통된 모듈 사용 
@PropertySource("classpath:/datasources/datasource.properties")
//어느 패키지에 있는 매퍼를 다 스캔  / 디비 쿼리 날리는 어노테이션
@MapperScan("com.example.demo.web")
public class DataSourceConfig {
//	public EnvironmentStringPBEConfig environmentVariablesConfiguration() {
//		EnvironmentStringPBEConfig environmentStringPBEConfig = new EnvironmentStringPBEConfig();
//		environmentStringPBEConfig.setAlgorithm("PBEWithMD5AndDES");
//		environmentStringPBEConfig.setPasswordEnvName("APP_ENCRYPTION_PASSWORD");
//		return environmentStringPBEConfig;
//	}
//
//	public StandardPBEStringEncryptor configurationEncryptor() {
//		StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
//		standardPBEStringEncryptor.setConfig(environmentVariablesConfiguration());
//		standardPBEStringEncryptor.setPassword("d_'1[5aiuKoj&6)L");
//		return standardPBEStringEncryptor;
//	}
}
