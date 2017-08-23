<?php 

require_once '../lib/PHPExcel.php';
require_once '../lib/PHPExcel/Writer/Excel2007.php';
require_once '../lib/PHPExcel/Writer/Excel5.php';
require_once  '../lib/PHPExcel/IOFactory.php';
function getExcel($fileName,$headArr,$data){
    if(empty($data) || !is_array($data)){
        die("data must be a array");
    }
    if(empty($fileName)){
        exit;
    }
    $date = date("Y_m_d",time());
    $fileName = "_{$date}.xls";
 
    //?建新的PHPExcel?象
    $objPHPExcel = new PHPExcel();
    $objProps = $objPHPExcel->getProperties();
     
    //?置表?
    $key = ord("A");
    foreach($headArr as $v){
        $colum = chr($key);
        $objPHPExcel->setActiveSheetIndex(0) ->setCellValue($colum.'1', $v);
        $key += 1;
    }
     
    $column = 2;
    $objActSheet = $objPHPExcel->getActiveSheet();
	
	$count = count($data);
	$countS = count($data[0]);
	// echo $countS;
	$span = ord("A");
	for($j=0;$j<$countS;$j++){
		$k=$j+2;
		// $k = chr($span);
		/**
		for($i=0;$i<$count;$i++){
			$k=$j+2;
			$m = chr($span);
			$objActSheet->setCellValue($m.$k, $data[$i][$j]);
			$span++;
			if($span>71){
				$span=ord("A");
			}
		}*/
		$objActSheet->setCellValue("A".$k, $data[0][$j]);
		$objActSheet->setCellValue("B".$k, $data[1][$j]);
		$objActSheet->setCellValue("C".$k, $data[2][$j]);
		$objActSheet->setCellValue("D".$k, $data[3][$j]);
		$objActSheet->setCellValue("E".$k, $data[4][$j]);
		$objActSheet->setCellValue("F".$k, $data[5][$j]);
		$objActSheet->setCellValue("G".$k, $data[6][$j]);
		// echo $data[$i][$j];
	}
			 
	
    // foreach($data as $key => $rows){ //行?入
        // $span = ord("A");
        // foreach($rows as $keyName=>$value){// 列?入
            // $j = chr($span);
            // $objActSheet->setCellValue($j.$column, $value);
            // $span++;
        // }
		
        // $column++;
    // }
 
    $fileName = iconv("utf-8", "gb2312", $fileName);
    //重命名表
    $objPHPExcel->getActiveSheet()->setTitle('Simple');
    //?置活??指?到第一?表,所以Excel打??是第一?表
    $objPHPExcel->setActiveSheetIndex(0);
    //??出重定向到一?客?端web??器(Excel2007)
	// header("Content-type:application/vnd.ms-excel;charset=UTF-8");
	// header('Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
	// header("Content-Disposition: attachment; filename=\"$fileName\"");
	// header('Cache-Control: max-age=0');
	$objWriter = PHPExcel_IOFactory::createWriter($objPHPExcel, 'Excel2007');
	// $objWriter->save('php://output');
	$objWriter->save($fileName);
	// exit;
	return $fileName;
}
?>