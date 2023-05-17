const {createApp} = Vue

createApp({
    data(){
        return{
            datos:[],
            cards:[],
            cardDebit:[],
            cardCredit:[],
            cardActive:[],
            actualDate:""
        }
    },
    created(){
       this.loadData()
    },
    methods: {
        loadData(){
            axios.get('http://localhost:8080/api/clients/current')
            .then(response => {
                this.datos = response.data;
                this.cards = this.datos.cards
                console.log(this.cards);
                this.cardDebit = this.datos.cards.filter(card => card.type==="DEBIT" && card.active)
                this.cardCredit = this.datos.cards.filter(card => card.type==="CREDIT" && card.active)
                this.cardActive = this.cards.filter(card => card.active)

                this.actualDate = new Date().toLocaleDateString().split(",")[0].split("/").reverse().join("-");

                console.log(this.cardCredit);
                console.log(this.cardDebit); 
                console.log(this.datos);
              
            } )
        },
        cardDelete(id){
            axios.put(`http://localhost:8080/api/clients/current/cards/${id}`) 
            .then(response => {
                window.location.href="/web/html/cards.html"
            })
            .catch(error=> console.log("eliminada"))
        },
        logout(){
            Swal.fire({
                title: 'Are you sure that you want to log out',
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Sure',
                showLoaderOnConfirm: true,
                preConfirm: (login) => {
                    return axios.post('/api/logout')
                        .then(response => {
                            window.location.href="/web/html/index.html"
                        })
                        .catch(error => {
                            Swal.showValidationMessage(
                                "Request failed: ${error}"
                            )
                        })
                },
                allowOutsideClick: () => !Swal.isLoading()
            })
        }
    
    
    
         }
}).mount("#app")