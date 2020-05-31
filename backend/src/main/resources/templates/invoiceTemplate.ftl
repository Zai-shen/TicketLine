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
        th {
            border-bottom: 1px solid black;
        }
        .seller {
            text-align: right;
        }
        .date {
            text-align: right;
        }
        .content {
            width: 100%;
        }
        .sum {
            margin-left: auto;
        }
        .total-sum {
            border-bottom: 1px double black;
            border-top: 1px solid black;
        }
    </style>
</head>
<body>
    <div class="seller">
        <p>Ticketline</p>
        <p>Musterstraße 1</p>
        <p>1040 Wien, AT</p>
    </div>
    <div>
        <p>${data.buyer.firstname} ${data.buyer.lastname}</p>
        <p>${data.buyer.address.street} ${data.buyer.address.housenr}</p>
        <p>${data.buyer.address.postalcode} ${data.buyer.address.city}, ${data.buyer.address.country}</p>
    </div>
    <div class="date">
        <p>${data.booking.date}</p>
    </div>
    <p>Rechnung Nr. ${data.booking.id}</p>
    <table class="content">
        <tr>
            <th>Menge</th>
            <th>Beschreibung</th>
            <th>Preis (netto)</th>
            <th>Summe</th>
        </tr>
        <tr>
            <td>${data.getAmount()}</td>
            <td>${data.booking.performance.event.title}</td>
            <td>Preis (netto)</td>
            <td>Summe</td>
        </tr>
    </table>

    <table class="sum">
        <tr>
            <td>Summe netto</td>
            <td>00,00 €</td>
        </tr>
        <tr>
            <td>zzgl. USt. 13,00 %</td>
            <td>00,00 €</td>
        </tr>
        <tr>
            <td class="total-sum">Gesamtsumme brutto</td>
            <td class="total-sum">00,00 €</td>
        </tr>
    </table>
    <p>Leistungsdatum ist gleich dem Rechnungsdatum</p>
    <p>UID: ATU12345678</p>
</body>
</html>