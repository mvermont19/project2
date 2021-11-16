sshpass -p maria_dev ssh -p 2222 maria_dev@sandbox-hdp.localhost \
"if [ -e \"project2\" ]
then
  echo \"Found project directory 'project2'\"
else
  mkdir project2
  echo \"Created directory 'project2'\"
fi"

sshpass -p maria_dev scp -P 2222 run.sh maria_dev@sandbox-hdp.localhost:/home/maria_dev/project2
sshpass -p maria_dev scp -P 2222 target/scala-2.11/project2.jar maria_dev@sandbox-hdp.localhost:/home/maria_dev/project2