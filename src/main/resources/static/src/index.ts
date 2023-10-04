
const getAllCustomers = () => {
    const url = "./customers/all"
    fetch(url)
        .then(res => {
            if (res.status != 200) {

            }
            return res.json()
        })
        .then(json => {
            displayCustomers(json);
        })
        .catch(err => {
            console.log(err.toString());
        })
}

const displayCustomers = (data) => {
    let content: string = '';
    data.forEach(c => {
        content += `${c.lastName} - `;
    })
    console.log(content);
}