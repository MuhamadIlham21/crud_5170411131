<?php
 
    include 'db_connect.php';
    $response = array();
     
    //Check for mandatory parameters
    if(isset($_POST['id_fakultas'])) {
        
        $id_fakultas = $_POST['id_fakultas'];
    
            
        $query = "DELETE FROM fakultas WHERE id_fakultas=?";
        //Prepare the query
        if($stmt = $con->prepare($query)){
            //Bind parameters
            $stmt->bind_param("i",$id_fakultas);
            //Exceting MySQL statement
            $stmt->execute();
            //Check if data got inserted
            if($stmt){
                $response["success"] = 1;           
                $response["message"] = "Sukses Delete fakultas";          
                
            }else{
                //Some error while inserting
                $response["success"] = 0;
                $response["message"] = "Gagal Delete fakultas";
            }                   
        }else{
            //Some error while inserting
            $response["success"] = 0;
            $response["message"] = mysqli_error($con);
        }
     
    }else{
        //Mandatory parameters are missing
        $response["success"] = 0;
        $response["message"] = "";
    }
    //Displaying JSON response
    echo json_encode($response);
?>