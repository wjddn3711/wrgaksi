<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>


<!DOCTYPE html>
<html class="wide wow-animation" lang="ko">
<head>
    <title>에러페이지</title>
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="utf-8">
    <mytag:icon />
    <!-- Stylesheets-->
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Roboto:100,300,300i,400,500,600,700,900%7CRaleway:500">
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/fonts.css">
    <link rel="stylesheet" href="css/style.css">
    <!--[if lt IE 10]>
    <div style="background: #212121; padding: 10px 0; box-shadow: 3px 3px 5px 0 rgba(0,0,0,.3); clear: both; text-align:center; position: relative; z-index:1;"><a href="http://windows.microsoft.com/en-US/internet-explorer/"><img src="images/ie8-panel/warning_bar_0000_us.jpg" border="0" height="42" width="820" alt="You are using an outdated browser. For a faster, safer browsing experience, upgrade for free today."></a></div>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <mytag:preloader />
    <div class="page">

        <!-- Page Header-->
        <mytag:pageHeader pageSector=""/>

        <!-- Tell-->
        <section class="section section-sm section-first bg-default">
            <div class="container">
                <h3 class="heading-3">404 에러</h3>
                <div class="group-custom-1 group-middle oh-desktop">
                    <!-- Quote Classic-->
                    <article class="quote-classic quote-classic-3 wow slideInDown">
                        <div class="quote-classic-text">
                            <p class="q">요청하신 페이지가 존재하지 않습니다.</p><hr>
                            <p class="q2">올바른 요청을 부탁드립니다!</p>
                        </div>
                        <button class="button button-lg button-primary button-winona wow fadeInRight" onclick="history.back()" >뒤로가기</button>
                    </article>
                </div>
            </div>
        </section>

        <!-- Page Footer-->
        <mytag:pageFooter />
    </div>

    <!-- Javascript-->
    <script src="js/core.min.js"></script>
    <script src="js/script.js"></script>
    <!-- coded by 마른오징어-->
    </body>
</html>