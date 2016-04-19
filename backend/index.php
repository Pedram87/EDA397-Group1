<?php
    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Methods: POST,GET,OPTIONS');
    header('Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept');
    header('Content-type: text/html; charset=utf8mb4');
    mysql_set_charset('utf8mb4');

    // Input JSON string
    $array = json_decode(file_get_contents('php://input'), true);

    // Check if file contains data
    if ($array && count($array > 0)) {
        // Include database functions
        require_once("DB_Functions.php");
        $db = new DB_Functions();

        mysql_query("SET CHARACTER SET utf8mb4");
        mysql_query("SET NAMES utf8mb4");            
        
        // Store JSON tag
        $tag = $array["tag"];

        // Check if tag exists
        if ($tag && $tag != "") {
            $email              = $array["email"];
            $name               = $array["name"];
            $password           = $array["password"];

            // Set response JSON to default values
            $response = array("tag" => $tag, "success" => 0, "error" => 0);

            // Check the tag
            if ($tag == "register") {
                // Register user
                $userExists = $db->isUserExisting($email);

                // Check if the user already exists
                if (!$userExists) {
                    // Generate a secure password
                    $hash = password_hash($password, PASSWORD_DEFAULT);
                    $bool = $db->registerUser($email, $name, $hash);

                    if ($bool) {
                        $response["success"]         = 1;
                    } else {
                        $response["error"]           = 1;
                        $response["error_msg"]       = "Error, could not register";
                    }                    
                } else {
                    $response["error"]           = 1;
                    $response["error_msg"]       = "Error, email already exists";                    
                }
                echo json_encode($response);
            } else {
            	// Error in tag
                echo "Invalid action tag!";
            }
        } else {
            echo "Access denied!";
        }
    } else {
        echo "Indata failure";
    }
?>