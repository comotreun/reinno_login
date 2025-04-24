$(function() {
    $('#singup').on('click', function(e) {
        e.preventDefault();
        console.log("회원가입 버튼 클릭");

        const data = {
            userName: $('#signup_user_name').val(),
            userPw: $('#signup_user_passwd').val(),
            userEmail: $('#signup_user_email').val(),
            userTel: $('#signup_user_tel').val()
        };

        $.ajax({
            url: '/member/signup',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(result) {
                alert('회원가입 성공');
            },
            error: function(xhr, status, error) {
                alert('회원가입 실패: ' + error);
            }
        });
    });

    $('#login_btn').on('click', function(e) {
        e.preventDefault();
        console.log("로그인 버튼 클릭");

        const data = {
            userEmail: $('#user_email').val(),
            userPw: $('#user_passwd').val(),
        };

        $.ajax({
            url: '/member/login',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(result) {
                alert('로그인 성공');
				
				window.location.href = '/home';
            },
            error: function(xhr, status, error) {
                alert('로그인 실패: ' + error);
            }
        });
    });
});
