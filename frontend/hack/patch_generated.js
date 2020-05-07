const replace = require('replace-in-file');
console.log("Starting replacement")
const options = {
  files: 'src/generated/**',
  from: /import .*/g,
  to: (match) => {
    return match.replace('|',',')
  },
};
replace.sync(options);
console.log("Finished replacement")
