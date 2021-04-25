
if [ "$#" -ne 5 ];
    then 
        echo "You need pass 5 param"
        exit 1
fi
cd $1
./$2 stop
./$2 start
cd $3
( tail -f -n10 $4 & ) | grep -q $5


