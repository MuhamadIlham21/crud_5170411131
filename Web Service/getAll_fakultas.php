<?php
 
    include 'db_connect.php';

    $query = "SELECT * FROM fakultas";
    $result = array();
    $movieArray = array();
    $response = array();
    //Prepare the query
    if($stmt = $con->prepare($query)){
        $stmt->execute();
    
        $stmt->bind_result($id_fakultas,$kode_fakultas,$nama_fakultas);
    
        while($stmt->fetch()){
    
            $movieArray["id_fakultas"] = $id_fakultas;
            $movieArray["kode_fakultas"] = $kode_fakultas;
            $movieArray["nama_fakultas"] = $nama_fakultas;            
            $result[]=$movieArray;
            
        }
        $stmt->close();
        $response["success"] = 1;
        $response["data"] = $result;
        
     
    }else{
        //Some error while fetching data
        $response["success"] = 0;
        $response["message"] = mysqli_error($con);
            
        
    }
    
    echo json_encode($response);
?>