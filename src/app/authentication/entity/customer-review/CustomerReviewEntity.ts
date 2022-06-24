export class CustomerReview {
    id:number;
    comment:string;
    rating:number;
    productName:string;
    userName:string;
    title :string;
    constructor(id:number,comment:string,rating:number,productName:string,userFullName:string,title:string){
        this.comment = comment;
        this.rating = rating;
        this.id = id;
        this.title = title;
        this.productName = productName;
        this.userName = userFullName;
    }
}