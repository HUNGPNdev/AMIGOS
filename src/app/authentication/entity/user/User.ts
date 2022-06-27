import { Role } from "../role/role";

export class User {
    id: number;
    userName: string;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    address: string;
    password: string;
    is_deleted: boolean;
    create_at: Date;
    update_at: Date;
    roles?:Role[] =[];
    roleId?:number[];
}