<#setting locale='de_AT'>
<!DOCTYPE html>
<html>
<head>
    <Title>Rechnung</Title>
    <style>
        * {
                font-family: 'Roboto', serif;
            }
        p {
            margin: 0;
        }
        .seller {
            text-align: right;
        }
        .date {
            text-align: right;
        }
    </style>
</head>
<body>
    <div>
        <p>${data.buyer.firstname} ${data.buyer.lastname}</p>
        <p>${data.buyer.address.street} ${data.buyer.address.housenr}</p>
        <p>${data.buyer.address.postalcode} ${data.buyer.address.city}, ${data.buyer.address.country}</p>
    </div>
    <div class="seller">
        <p>Ticketline</p>
        <p>Musterstraße 1</p>
        <p>1040 Wien, AT</p>
    </div>
    <div class="date">
        <p>Datum</p>
    </div>
    <p>Rechnung Nr. ${data.booking.id}</p>
    <p>${data.getAmount()}</p>
</body>
</html>