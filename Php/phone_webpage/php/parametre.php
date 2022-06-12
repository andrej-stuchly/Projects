<!DOCTYPE html>
<html lang="sk">
<head>
	<meta charset="UTF-8">
	<title>LG Q60 - Parametre/Parameters</title>
	<link rel="stylesheet" type="text/css" href="../css/css.css?css=<?php echo time(); ?>">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=yes">
</head>
<body>
	<header>
		<?php include 'php_doplnky/header.php';?>
	</header>
	<nav>
		<?php $stranka = 2; include 'php_doplnky/nav.php';?>
	</nav>
	<?php
		$lang = $_GET['lang'];
		if ($lang == "eng"){
			include 'php_doplnky/eng.php';
		}
		else{
			include 'php_doplnky/sk.php';
		}
		echo "<p>".$par."</p>";
		echo "<table>";
			$i = 0;
			echo "<tr>";
				while ($i < 2){
					echo "<th>".$par_tab[$i]."</th>"; $i++;
				}
			echo "</tr>";
			while ($i < 39){
				echo "<tr>";
					for ($j = 1; $j < 3; $j++){
						echo "<td>".$par_tab[$i]."</td>"; $i++;
					}
				echo "</tr>";
			}
		echo "</table>";
	?>
</body>
</html>
