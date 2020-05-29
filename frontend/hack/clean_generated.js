const fs = require('fs');
console.log("Cleaning src/generated");
fs.rmdirSync('src/generated', { recursive: true });
