<!DOCTYPE html>
<html lang="sk">
<head>
	<meta charset="UTF-8">
	<title>LG Q60 - FAQ</title>
	<link rel="stylesheet" type="text/css" href="../css/css.css?css=<?php echo time(); ?>">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=yes">
</head>
<body>
	<header>
		<?php include 'php_doplnky/header.php';?>
	</header>
	<nav>
		<?php $stranka = 4; include 'php_doplnky/nav.php';?>
	</nav>
	<?php
		$lang = $_GET['lang'];
		if ($lang == "eng"){
			include 'php_doplnky/eng.php';
		}
		else{
			include 'php_doplnky/sk.php';
		}
		echo "<p>".$faq['text1']."</p><br>";
		echo "<div class=\"faq_div\">";
		$i = 2; $j = "text$i";
			while ($i < 12){
				echo "<p><b>".$faq[$j]."</b></p>";$i++; $j = "text$i";
				echo "<p>".$faq[$j]."</p>";$i++; $j = "text$i";
				if ($i == 4 || $i == 6 ||$i == 8 ||$i == 10){
					echo "<hr>";
				}
			}
		echo "</div>"	
	?>
</body>
</html>
