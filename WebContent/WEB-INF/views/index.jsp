    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
        <title>VOD</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Expires" content="0">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Cache-control" content="no-cache">
        <meta http-equiv="Cache" content="no-cache">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/dist/css/bootstrap.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/dist/css/bootstrap-select.css"/>">


        <script type="text/javascript" src="<c:url value="/resources/jsLib/jquery.min.js"/>"></script>
<%--         <script type="text/javascript" src="<c:url value="/resources/jsLib/jquery-1.9.0.js"/>"></script> --%>
        <script type="text/javascript" src="<c:url value="/resources/dist/js/bootstrap.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/dist/js/bootstrap-select.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/utility.js"/>"></script>
        <script type="text/javascript">
        $(document).ready(function(){
        $("button").click(function() {
            var req="PICK0014353902";
            var url="<c:url value="/search"/>";
            ajaxCallForEventWithJsonData(null, url, req, null, function (that, data) {
              if (data) {
                alert("success.");
              }
            })
            });
        });
        </script>
        </head>
        <body>00
        <div class="form-group">
        <select class="selectpicker" data-live-search="true" title="Please select a lunch ...">
        <option>Hot Dog, Fries and a Soda</option>
        <option>Burger, Shake and a Smile</option>
        <option>Sugar, Spice and all things nice</option>
        <option>Baby Back Ribs</option>
        <option>A really really long</option>
        </select>
        </div>
        <div class="g-hd" id="headerWrap"></div>
        <div class="g-bd" id="mainContentWrap"></div>
        <div class="g-ft" id="footerWrap"></div>
        <button>Click me</button>
        </body>
        </html>
