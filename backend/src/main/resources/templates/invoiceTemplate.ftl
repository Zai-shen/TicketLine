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
            margin-top: 4em;
            text-align: right;
        }
        .buyer {
            margin-top: 2em;
            margin-bottom: 4em;
        }
        .date {
            text-align: right;
        }
        .content {
            margin-top: 2em;
            width: 100%;
        }
        .sum {
            border-top: 1px solid black;
            margin-left: auto;
            margin-bottom: 6em;
        }
        .align-right {
            text-align: right;
        }
        .total-sum {
            border-bottom: 1px double black;
            border-top: 1px solid black;
        }
    </style>
</head>
<body>
    <div class="seller">
        <p>${data.getName()}</p>
        <p>${data.getStreet()} ${data.getHousenr()}</p>
        <p>${data.getPostalcode()} ${data.getCity()}, ${data.getCountry()}</p>
    </div>
    <div class="buyer">
        <p>${data.buyer.firstname} ${data.buyer.lastname}</p>
        <p>${data.buyer.address.street} ${data.buyer.address.housenr}</p>
        <p>${data.buyer.address.postalcode} ${data.buyer.address.city}, ${data.buyer.address.country}</p>
    </div>
    <div class="date">
        <p>${data.formatDate()}</p>
    </div>
    <b><#if data.cancelled>Stornierung für Rechnung Nr.<#else>Rechnung Nr.</#if> ${data.booking.id}</b>
    <table class="content">
        <tr>
            <th>Menge</th>
            <th>Beschreibung</th>
            <th class="align-right">Preis (netto)</th>
        </tr>
        <#list data.booking.tickets as ticket>
            <tr>
                <td>${ticket.formatAmount()}</td>
                <td>${data.booking.performance.event.title}</td>
                <td class="align-right">${ticket.formatPriceExclVAT()} €</td>
            </tr>
        </#list>
    </table>

    <table class="sum">
        <tr>
            <td>Summe netto</td>
            <td class="align-right"><#if data.cancelled>- </#if>${data.formatPrice()} €</td>
        </tr>
        <tr>
            <td>zzgl. USt. 13,00 %</td>
            <td class="align-right"><#if data.cancelled>- </#if>${data.formatVAT()} €</td>
        </tr>
        <tr>
            <td class="total-sum">Gesamtsumme brutto</td>
            <td class="total-sum align-right"><#if data.cancelled>- </#if>${data.getTotalPrice()} €</td>
        </tr>
    </table>
    <p>Leistungsdatum ist gleich dem Rechnungsdatum</p>
    <p>UID: ${data.getUID()}</p>
</body>
</html>