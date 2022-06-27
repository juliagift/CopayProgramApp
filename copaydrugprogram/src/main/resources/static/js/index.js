// Validates that all answers are YES. If any are NO, then prints a message, and does not allow
// user to continue to the Registration Page.
var validateInput = () => {
	try {
		if (
			document.getElementById("citizen_yes").checked &&
			document.getElementById("insurance_yes").checked &&
			document.getElementById("age_yes").checked

		) {
			return true;
		}
		document.getElementById("error_home_msg").innerHTML =
			"I'm sorry you're not eligible for this program!";
	} catch (e) { }
	return false;
};

// Validates that answer is YES. If NO, then prints a message.
var validateClaimInput = () => {
	try {
		if (
			document.getElementById("claim_yes").checked
		) {
			return true;
		}
		document.getElementById("error_claim_msg").innerHTML =
			"Click Yes to submit a claim.";
	} catch (e) { }
	return false;
};

