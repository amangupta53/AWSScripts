Write-Host "Loggin in...."
Set-AWSCredentials -AccessKey 'AKIAIRM4MUWUKGVF2XRQ' -SecretKey '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'
Write-Host "us-east-1"
(Get-EC2Instance -Region us-east-1)[0].RunningInstance | Format-Table
Write-Host "us-east-2"
Get-EC2Instance -Region us-east-2
Write-Host "us-west-1"
Get-EC2Instance -Region us-west-1
Write-Host "us-west-2"
Get-EC2Instance -Region us-west-2
Write-Host "ap-northeast-1"
Get-EC2Instance -Region ap-northeast-1
Write-Host "ap-northeast-2"
Get-EC2Instance -Region ap-northeast-2
Write-Host "ap-south-1"
Get-EC2Instance -Region ap-south-1
Write-Host "ap-southeast-1"
Get-EC2Instance -Region ap-southeast-1
Write-Host "ap-southeast-2"
Get-EC2Instance -Region ap-southeast-2
Write-Host "eu-central-1"
Get-EC2Instance -Region eu-central-1
Write-Host "eu-west-1"
Get-EC2Instance -Region eu-west-1
Write-Host "eu-west-2"
Get-EC2Instance -Region eu-west-2
Write-Host "sa-east-1"
Get-EC2Instance -Region sa-east-1
Write-Host "ca-central-1"
Get-EC2Instance -Region ca-central-1