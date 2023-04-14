const { createApp } = Vue
// console.log("hola");

createApp({
    data(){
    return{
        datos:[],
        account:[],
        transactions:[],
        id : (new URLSearchParams(location.search)).get("id")
     
    }
},

created(){
    this.loadData()
    console.log(this.id);

},
  methods: {
    loadData(){
        axios.get('http://localhost:8080/api/accounts/' + this.id)
        .then(response => {

            this.account = response.data;
            console.log(response);
            // console.log(this.account);
            this.transactions = this.account.transaction
             console.log(this.transactions);
            this.transactions.sort((transaction1,transaction2)=> {
                return (transaction1.date.slice(0,4) + transaction1.date.slice(5,7) + transaction1.date.slice(8,10)) -( transaction2.date.slice(0,4)+ transaction2.date.slice(5,7)+ transaction2.date.slice(8,10))
            } );
          
        } )
        //  .catch(error => console.log(error));
    },

     }


}).mount("#app")