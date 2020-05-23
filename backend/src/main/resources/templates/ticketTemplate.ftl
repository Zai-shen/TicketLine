<#setting locale='de_AT'>
<!DOCTYPE html>
<html>
<head>
    <Title>Digitales Ticket ${data.event.title}</Title>
    <style>
        * {
            font-family: sans-serif;
        }
        .hero {
            width: 100%;
            background: #3f51b5;
            color: white;
        }
        .description {
            margin: 2em;
        }
        .ticket {
            background: url("data:image/png;base64,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");
            border: medium solid black;
        }
        img {
            border: medium solid black;
            margin: 2em;
        }
        h1 {
            margin: 0;
        }
    </style>
</head>
<body>
<div class="ticket">
    <div class="hero">
        <h1>
            ${data.event.title}
        </h1>
        <h2>
            ${data.seat}
        </h2>
    </div>
    <div>
        <ul>
            <li><b>Dauer:</b> ${data.event.duration} Minuten</li>
            <li><b>Kategorie</b> ${data.event.category}</li>
        </ul>
        <p class="description">
            ${data.event.description}
        </p>
        <img src="${data.getBarcode()}" alt="${data.getUUID().toString()}"/>
    </div>
</div>
</body>
</html>