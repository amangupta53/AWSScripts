
. .\Modules\Region\GetRegions.ps1

Write-Host "Loggin in..."
. .\Modules\Credentials\Login.ps1

foreach ($r in GetRegions) {
    Write-Host "Region: " $r
    try {
        (Get-EC2Instance -Region $r)[0].RunningInstance | Format-Table 
    }
    catch {
        Write-Host "No Instances in Region:" $r
    }
}