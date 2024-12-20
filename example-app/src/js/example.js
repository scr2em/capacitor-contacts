import { CapacitorContacts } from '@scr2em/capacitor-contacts';

let vCardString = `
BEGIN:VCARD
VERSION:3.0
N:Doe;John;;;
FN:John Doe
ORG:Acme Corp
TITLE:Software Engineer
TEL;TYPE=WORK,VOICE:+1-555-123-4567
TEL;TYPE=CELL,VOICE:+1-555-987-6543
EMAIL;TYPE=INTERNET:john.doe@example.com
URL:https://johndoe.com
ADR;TYPE=WORK:;;123 Tech Street;Silicon Valley;CA;94025;USA
END:VCARD`


window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    CapacitorContacts.openAddContact({ vcardString: vCardString })
}
