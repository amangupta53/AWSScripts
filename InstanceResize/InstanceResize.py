import boto3
import time

id = 'ins_id'
ec2 = boto3.resource('ec2', aws_access_key_id='ACCESS_KEY', aws_secret_access_key='SECRET_KEY')

ins = ec2.Instance(id)

if ins.state['Code'] != 80:
    ins.stop()

while True:
    ins = ec2.Instance(id)
    code = ins.state['Code']
    if code == 80:
        print('Instance stopped.')
        break
    else:
        time.sleep(20)
        print('Instance stopping.')

ins = ec2.Instance(id)

print('Resizing Instance')

response = ins.modify_attribute(
    InstanceType={
            'Value': 't2.micro'
        }
)

choice = input('Instance resized successfully. Start ins? (Yes/No)')
if choice == 'Yes':
	ins.start()