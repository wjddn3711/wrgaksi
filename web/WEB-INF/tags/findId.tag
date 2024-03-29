<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
    $(function(){
        $("#findIdBtn").click(function(){
            $.ajax({
                url : "findId.me",
                type : "POST",
                async: true,
                data : {
                    customer_name : $("#name").val(),
                    phone_number : $("#phoneNumberId").val()
                },
                success : function(result) {
                    alert(result);
                },
                error: function () {
                    alert("서버요청실패");
                }
            })
        });
    })
</script>
<style type="text/css">
    .mybtn{
        width:150px;
        height:40px;
        padding:0;
        display:inline;
        border-radius: 4px;
        background: #212529;
        color: #fff;
        margin-top: 20px;
        border: solid 2px #212529;
        transition: all 0.5s ease-in-out 0s;
    }
    .mybtn:hover .mybtn:focus {
        background: white;
        color: #212529;
        text-decoration: none;
    }
</style>
<body>
<div class="w3-content w3-container w3-margin-top">
    <div class="w3-container w3-card-4 w3-auto" style="width: 382px;height: 456.3px;">
        <div class="w3-center w3-large w3-margin-top">
            <h3>아이디 찾기</h3>
        </div>
        <div>
            <p>
                <label>이름</label>
                <input class="w3-input" type="text" id="name" name="name" placeholder="회원가입한 이름을 입력하세요" required>
            </p>
            <p>
                <label>전화번호</label>
                <input class="w3-input" type="text" id="phoneNumberId" name="phoneNumberId" placeholder="회원가입한 전화번호를 입력하세요" required>
            </p>
            <p class="w3-center">
                <button type="button" id="findIdBtn" class="w3-button w3-hover-white w3-ripple w3-margin-top w3-round mybtn">찾기</button>
                <button type="button" onclick="history.go(-1);" class="w3-button w3-hover-white w3-ripple w3-margin-top w3-round mybtn">로그인으로</button>
            </p>
        </div>
    </div>
</div>