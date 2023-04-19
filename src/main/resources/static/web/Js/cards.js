const {createApp} = Vue

createApp({
    data(){
        return{
            datos:[],
            cards:[],
            cardDebit:[],
            cardCredit:[]
        }
    },
    created(){
       this.loadData()
    },
    methods: {
        loadData(){
            axios.get('http://localhost:8080/api/clients/1')
            .then(response => {
                this.datos = response.data;
                this.cards = this.datos.cards
                console.log(this.cards);
                this.cardDebit = this.datos.cards.filter(card => card.type==="DEBIT")
                this.cardCredit = this.datos.cards.filter(card => card.type==="CREDIT")
                console.log(this.cardCredit);
                console.log(this.cardDebit);
                
                console.log(this.datos);
              
            } )
        }
    
    
    
         }
}).mount("#app")