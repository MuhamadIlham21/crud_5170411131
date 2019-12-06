<?php
 
    include 'db_connect.php';
    $response = array();
     
    //Check for mandatory parameters
    if(isset($_POST['id_fakultas'])&&($_POST['kode_fakultas'])&&isset($_POST['nama_fakultas'])) {
        
        $id_fakultas = $_POST['id_fakultas'];
        $kode_fakultas = $_POST['kode_fakultas'];
        $nama_fakultas = $_POST['nama_fakultas'];        
    
            
        $query = "UPDATE fakultas set kode_fakultas=?, nama_fakultas=? WHERE id_fakultas=?";
        //Prepare the query
        if($stmt = $con->prepare($query)){
            //Bind parameters
            $stmt->bind_param("ssi",$kode_fakultas,$nama_fakultas,$id_fakultas);
            //Exceting MySQL statement
            $stmt->execute();
            //Check if data got inserted
            if($stmt){
                $response["success"] = 1;           
                $response["message"] = "Sukses Update fakultas";          
                
            }else{
                //Some error while inserting
                $response["success"] = 0;
                $response["message"] = "Gagal Update fakultas";
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