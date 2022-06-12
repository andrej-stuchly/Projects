<!DOCTYPE html>
<html lang="sk">
<head>
	<meta charset="UTF-8">
	<title>LG Q60 - Recenzie/Reviews</title>
	<link rel="stylesheet" type="text/css" href="../css/css.css?css=<?php echo time(); ?>">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=yes">
</head>
<body>
	<header>
		<?php include 'php_doplnky/header.php';?>
	</header>
	<nav>
		<?php $stranka = 6; include 'php_doplnky/nav.php';?>
	</nav>
	<?php
		$lang = $_GET['lang'];
		if ($lang == "eng"){
			include 'php_doplnky/eng.php';
			$url = "form.php?lang=eng";
		}
		else{
			include 'php_doplnky/sk.php';
			$url = "form.php";
		}
		echo "<p>".$rev['text1']."</p><br>";
		$i = 2; $j = "text$i";
		while ($i<7){
			echo "<div class=\"review_div\">";
				echo "<p>".$rev[$j]."</p>";
			echo "</div>";
			$i++; $j = "text$i";
		}
		$file = fopen("php_doplnky/recenzia.txt", "r");
		while(!feof($file)){
			$line = fgets($file, 1024);
			if ($line != NULL){
				echo "<div class=\"review_div\">";
					echo "<p>".$line."</p>";
				echo "</div>";
			}
		}
		echo "<br><p>".$rev['text7']."</p><br>";
		echo "<form action=\"$url\"method=\"post\">";
			echo "<p>".$rev['text8']."</p>";
			echo "<input type =\"text\" name=\"review\">";
			echo "<input type=\"submit\" value=\"Odoslať\">";
		echo"</form>";
	?>
</body>
</html>
