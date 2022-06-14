export class SigupInfo {
    lastName: string;
    userName: string;
    email: string;
    role: string[];
    password: string;
    firstName:string;
    address:string;
    phone :number;
    
    constructor(firstname: string,lastName:string, username: string, email: string, password: string, adress:string,phone:number) {
        this.firstName = firstname;
        this.lastName = lastName;
        this.userName = username;
        this.email = email;
        this.password = password;
        this.role = ['user'];
        this.address = adress;
        this.phone = phone;
    }
   
    
}
