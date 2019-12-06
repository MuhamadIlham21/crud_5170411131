<?php
 
    include 'db_connect.php';
    $response = array();
     
    
    if(isset($_POST['kode_fakultas'])&&isset($_POST['nama_fakultas'])){    

        $kode_fakultas = $_POST['kode_fakultas'];
        $nama_fakultas = $_POST['nama_fakultas'];
        
        
        $query = "INSERT INTO fakultas(kode_fakultas,nama_fakultas) VALUES (?,?)";
        //Prepare the query
        if($stmt = $con->prepare($query)){
            //Bind parameters
            $stmt->bind_param("ss",$kode_fakultas,$nama_fakultas);
            //Exceting MySQL statement
            $stmt->execute();
            //Check if data got inserted
            if($stmt){
                $response["success"] = 1;           
                $response["message"] = "Sukses input fakultas";          
                
            }else{
                //Some error while inserting
                $response["success"] = 0;
                $response["message"] = "Gagal input fakultas";
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