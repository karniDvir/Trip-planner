function validateDates() {
    const startDate = new Date(document.getElementById("startDate").value);
    const endDate = new Date(document.getElementById("endDate").value);
    const today = new Date().getFullYear() - 120;

    if(startDate.getFullYear() < today)
    {
        alert(`Start date must be after ${today}`);
        return false;
    }
    else if (endDate < startDate) {
        alert(`End date must be after start date`);
        return false;
    }
    else if(startDate.getFullYear() + 120 <= endDate.getFullYear()) {
        alert(`The average life expectancy is 71.33 so...`);
        return false;
    }
    return true;
}
