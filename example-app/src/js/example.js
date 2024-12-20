import { CapacitorContacts } from '@scr2em/capacitor-contacts';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    CapacitorContacts.echo({ value: inputValue })
}
