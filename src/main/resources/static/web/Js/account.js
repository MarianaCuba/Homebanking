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
        axios.get('http://localhost:8080/api/clients/current/accounts/' + this.id)
        .then(response => {

            this.account = response.data;
            console.log(response.data);
            this.transactions = this.account.transaction
             console.log(this.transactions);
            this.transactions.sort((transaction1,transaction2)=> {
                return (transaction1.date.slice(0,4) + transaction1.date.slice(5,7) + transaction1.date.slice(8,10)) -( transaction2.date.slice(0,4)+ transaction2.date.slice(5,7)+ transaction2.date.slice(8,10))
            } );
          
        } )
          .catch(error => console.log(error));
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
    },
     }


}).mount("#app")