const { createApp } = Vue
console.log("hola");
createApp({
    data(){
    return{
        datos:[],
        firstName: "" ,
        lastName:"",
        email:"",
        loanName:"",
        maxAmount:"",
        paymentsText:"",
        checked:[]
    }
},

created(){
    this.loadData()

},
  methods: {
    loadData(){
        axios.get('http://localhost:8080/api/clients')
        .then(response => {
          console.log(response)
            this.datos = response.data;
            console.log(this.datos);
        
        } )
         .catch(error => console.log(error));
    },
     addClient(){
        this.postClient();
     },
     postClient(){
        axios.post('http://localhost:8080/api/clients', {
            firstName: this.firstName,
            lastName: this.lastName,
            email : this.email
          })
          .then(function (response) {
            this.loadData();
          })
          .catch(function (error) {
            console.log(error);
          });
     },
     createLoan(){
      axios.post('/api/admin/loans', {
        name: this.loanName,
        maxAmount : this.maxAmount,
        payments : this.checked,
        
      }).then(response => {
       // this.loadData();
        window.location.href="./manager.html"})

      
     }

    }

//`name=${this.loanName}&amount=${this.maxAmount}&payments=${this.paymentsText}`
}).mount("#app")