<!DOCTYPE html>
<html lang="sk">
<head>
	<meta charset="UTF-8">
	<title>LG Q60 - Kontakt/Contact</title>
	<link rel="stylesheet" type="text/css" href="../css/css.css?css=<?php echo time(); ?>">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=yes">
</head>
<body>
	<header>
		<?php include 'php_doplnky/header.php';?>
	</header>
	<nav>
		<?php $stranka = 7; include 'php_doplnky/nav.php';?>
	</nav>
	<?php
		$lang = $_GET['lang'];
		if ($lang == "eng"){
			include 'php_doplnky/eng.php';
		}
		else{
			include 'php_doplnky/sk.php';
		}
		echo "<p>".$kon['text1']."</p>";
		echo "<br>";
		$j = 2; $k = "text$j";
		for ($i = 1; $i < 4; $i++){
			echo "<div class=\"contact_div\">";
				echo "<p><b>".$kon[$k]."</b></p>"; $j++; $k = "text$j";
				echo "<p>".$kon['text3']."</p>"; $j++; $k = "text$j";
				echo "<p>".$kon['text4']."</p>"; $j++; $k = "text$j";
			echo "</div>";
		}
		?>
</body>
</html>
