param (
    [string]$path
)
Get-ChildItem $path/resources/protos  -Filter *.proto |
        Foreach-Object {
            protoc --proto_path =$path/resources/protos --java_out =$path/src $_.name
        }