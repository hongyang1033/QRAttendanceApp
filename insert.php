<?PHP

$servername = "localhost";
$username   = "root";
$password   = "";
$dbname     = $_POST['dbname'];

$conn = mysqli_connect($servername, $username, $password, $dbname);

$tableName = $_POST['tableName'];
$name      = $_POST['name'];
$dateTime  = $_POST['dateTime'];
$id        = $_POST['id'];


$create = "INSERT INTO `$tableName` (`ID`,`Name`,`DateTime`) VALUES ('$id','$name','$dateTime')";


$result = mysqli_query($conn, $create);

if ($result > 0) {
    echo "success";
    exit;
} else {
    echo "failed";
    exit;
}




?>