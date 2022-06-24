export class CustomerReview{
     title : string;
     id : number;
     userId : number;
     productId:number;
     rating:number;
     comment:string;
     userName?:string;
     createAt:Date;
    constructor(title:string,comment:string,rating:number,productId:number,userName?:string){
        this.title =title;
        this.comment = comment;
        this.rating = rating;
        this.productId = productId;
        this.userName = userName;
    }
}