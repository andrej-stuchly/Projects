<?php
	echo "<img src=\"../visual/logo.svg\" alt=\"logo\" style =\"width:50px;height:60px;\" class=\"header\">";
	echo "<h1 class=\"header title\">LG Q60</h1>";
	$url = $_SERVER['REQUEST_URI'];
	$url = strtok($url, '?');
	echo "<ul class=\"language header\">";
		echo "<li><a href=\"$url\">SK</a></li>";
		echo "<li><a href=\"$url?lang=eng\">ENG</a></li>";
	echo "</ul>";
?>