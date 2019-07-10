<script type="text/JavaScript">
    function doRedirect() {
        atTime = "5000";
        toUrl = "/user/logout";

        setTimeout("location.href = toUrl;", atTime);
    }
</script>
<style>
   body {
    background: url(/resources/images/jail.jpg);
	background-repeat: repeat;
</style>
<body onload="doRedirect();">
</body>