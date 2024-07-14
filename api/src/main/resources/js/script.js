document.getElementById("emailCheckButton").addEventListener("click", function() {
    const email = document.getElementById("email").value;

    fetch('/users/check-email', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'email=' + encodeURIComponent(email) // email 값을 POST 데이터로 전송
    })
        .then(response => response.json())
        .then(data => {
            // 중복 여부에 따른 처리
            if (data.isDuplicate) {
                alert("이미 사용 중인 이메일입니다.");
                // 중복일 경우 입력 필드 초기화 등의 처리 추가
            } else {
                alert("사용 가능한 이메일입니다.");
            }
        })
        .catch(error => {
            console.error('중복 체크 오류:', error);
            // 오류 처리
        });
});