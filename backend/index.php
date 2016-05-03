<?php
    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Methods: POST');
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
            $email                  = $array["email"];
            $name                   = $array["name"];
            $password               = $array["password"];
            $task_id                = $array["task_id"];
            $task_name              = $array["task_name"];
            $task_duration          = $array["task_duration"];
            $task_owner             = $array["task_owner"];
            $task_PairProgrammer1ID = $array["task_PairProgrammer1ID"];
            $task_PairProgrammer2ID = $array["task_PairProgrammer2ID"];

            // Set response JSON to default values
            $response = array("tag" => $tag, "success" => 0, "error" => 0);

            // Check the tag
            if ($tag == "login") {
                // Check for user
                $user = $db->getUserByUserID($email);

                if ($user) {
                    $hash = $user["password"];

                    if (password_verify($password, $hash)) {
                        $response["success"]            = 1;
                        $response["user"]["email"]      = $user["email"];
                        $response["user"]["nickname"]   = $user["nickname"];
                    } else {
                        $response["error"]              = 1;
                        $response["error_msg"]          = "Password incorrect";
                    }
                } else {
                    $response["error"]                  = 1;
                    $response["error_msg"]              = "Could not find \"".$email."\"";
                }
                echo json_encode($response);
            } else if ($tag == "register") {
                // Register user
                $userExists = $db->isUserExisting($email);

                // Check if the user already exists
                if (!$userExists) {
                    // Generate a secure password
                    $hash = password_hash($password, PASSWORD_DEFAULT);
                    $bool = $db->registerUser($email, $name, $hash);

                    if ($bool) {
                        $response["success"]            = 1;
                    } else {
                        $response["error"]              = 1;
                        $response["error_msg"]          = "Error, could not register";
                    }                    
                } else {
                    $response["error"]                  = 1;
                    $response["error_msg"]              = "Error, email already exists";
                }
                echo json_encode($response);
            } else if ($tag == "getAllUsers") {
                $users = $db->getAllUsers();

                if ($users) {
                    $response["success"]                = 1;
                    // Loop through all users...
                    foreach($users as $key => $value) {
                        // ...and insert to JSON response string
                        $response["user"][$key] = $value;
                    }                    
                } else {
                    $response["error"]                  = 1;
                    $response["error_msg"]              = "Error finding users";
                }
                echo json_encode($response);
            } else if ($tag == "createTask") {
                $task = $db->createTask($task_name, $task_duration, $task_owner, $task_PairProgrammer1ID, $task_PairProgrammer2ID);

                if ($task) {
                    $response["success"]                = 1;
                } else {
                    $response["error"]                  = 1;
                    $response["error_msg"]              = "Error, could not create task";
                }
                echo json_encode($response);
            } else if ($tag == "deleteTask") {
                $task = $db->deleteTask($task_id);

                if ($task) {
                    $response["success"]                = 1;
                } else {
                    $response["error"]                  = 1;
                    $response["error_msg"]              = "Error, could not delete task";
                }
                echo json_encode($response);
            } else {
                // Error in tag
                $response["error"]                      = 1;
                $response["error_msg"]                  = "Error, invalid action tag";
                echo json_encode($response);
            }
        } else {
            echo "Access denied!";
        }
    } else {
        echo "Indata failure";
    }
?>