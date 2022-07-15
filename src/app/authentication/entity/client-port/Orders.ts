import { CartProductSize } from "./cart-product-size";

export class Orders {
    id: number;
    userId: number;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    address: string;
    createAt: Date;
    cartProductSizes: CartProductSize[];
    totalPrice: number;
    proId: number;
}
