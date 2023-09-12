mkdir -p ./data
cd data
SCRIPT_DIR="$(pwd)"
export JAVA_REPOSITORY_PATH=$SCRIPT_DIR
cd ../
mkdir -p ./out
find ./src/ -type f -name "*.java" | xargs javac -cp ./src/ -d ./out/
java -cp ./out/ Main 12312
