REM java -jar trang.jar xml/stat_sys_battery_charge_circle.xml        xsd/battanimation.xsd
xjc -d src_gen -p de.og.battanimation xsd/battanimation.xsd
