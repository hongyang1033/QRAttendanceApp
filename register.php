<?PHP

$servername = "localhost";
$username   = "root";
$password   = "";
$dbname     = "registeredUser";

$conn = mysqli_connect($servername, $username, $password, $dbname);

$username = $_POST['Username'];
$id       = $_POST['ID'];
$deviceID = $_POST['DeviceID'];



$sql = mysqli_query($conn, "SELECT * FROM `registered user` WHERE id = '$id' OR deviceID= '$deviceID'");
if (mysqli_num_rows($sql) >= 1) {
    echo "failed";
    exit;
} else {
    $create = "INSERT INTO `registered user` (`id`,`username`,`deviceID`) VALUES ('$id','$username','$deviceID')";
    $result = mysqli_query($conn, $create);
    if ($result > 0) {
        echo "success";
        exit;
    } else {
        echo "failed";
        exit;
    }
}


?>

