/**
 * Created by fabrice on 11/02/2016.
 */
var _ = require('lodash');
var fs = require('fs');

module.exports = {
    writeCommands: function(commands,output){
        var dataToWrite = commands.length + '\n';

        _(commands).forEach(function(command){
            dataToWrite = dataToWrite + command + '\n';
        });

        fs.writeFile(output, dataToWrite, function(err) {
            if(err) {
                return console.log(err);
            }
        });

    }
};
