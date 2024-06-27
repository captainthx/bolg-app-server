###  Documentation

## get started

#### generate rsa public & private KEYS
  - install openssl
  - make `certs` directory in resources
  - open terminal and active on `certs` directory
  - use commands at under
  -delete `keypair.pem` file
  - 
``` shell
openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```


