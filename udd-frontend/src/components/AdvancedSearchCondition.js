import { useState, useEffect } from "react";

const AdvancedSearchCondition = ({numOfElements, addConditionChanger, updateConditionChanger, removeConditionChanger, conditionIndex, condition}) => {
    const [category, setCategory] = useState("");
    const [text, setText] = useState("");
    const [degree, setDegree] = useState("");
    const [phraseQuery, setPhraseQuery] = useState(false);
    const [operator, setOperator] = useState("");

    useEffect(() => {
        if(condition){
        setCategory(condition.category)
        setText(condition.text)
        setDegree(condition.text)
        setPhraseQuery(condition.phraseQuery)
        setOperator(condition.operator)
        }
    }, []);

    const Validate = () => {
        if(conditionIndex!=numOfElements-1 && operator == "")
            return true;
        if(category!="" && category=="education"){
            if(degree=="")
                return true;
            return false;
        }else if(category!="" && category!="education"){
            if(text=="")
                return true;
            return false;
        }
        return true;
    }

    const dodajUslov = () => {
        addConditionChanger();
    }

    const ukloniUslov = () => {
        removeConditionChanger(conditionIndex);
        console.log(conditionIndex);
    }

    const azurirajUslov = (category, degree, text, phraseQuery, operator) => {
        let uslov = {
            index : conditionIndex,
            category: category,
            content: (category == "education" ? degree : text),
            phraseQuery: phraseQuery,
            operator: operator
        }
        updateConditionChanger(uslov);
    }

    const removeDisabled = () => {
        return !(numOfElements > 1);
    }

    const handleChangePhraseQuery = () => {
        let newValue = !phraseQuery;
        setPhraseQuery(!phraseQuery);
        azurirajUslov(category, degree, text, newValue, operator);
      };

      const handleSetCategory = (newValue) => {
        setCategory(newValue);
        azurirajUslov(newValue, degree, text, phraseQuery, operator);
      };

      const handleSetText = (newValue) => {
        setText(newValue);
        azurirajUslov(category, degree, newValue, phraseQuery, operator);
      };

      const handleSetDegree = (newValue) => {
        setDegree(newValue);
        azurirajUslov(category, newValue, text, phraseQuery, operator);
      };

      const handleSetOperator = (newValue) => {
        setOperator(newValue);
        azurirajUslov(category, degree, text, phraseQuery, newValue);
      };

    return (
        <><div className="h-100 d-flex justify-content-center align-items-center container rounded bg-white">
            <div className="">
                <div className="md-5">
                    <div className="row p-3 py-5">
                        <div className="col">
                        <div className="row mt-3">
                            <div className="col-md-12">
                                <label className="labels">Kategorija</label>
                                <select id="InputCategory"
                                    name="category"
                                    className="form-control"
                                    value={category}
                                    onChange={(e) => handleSetCategory(e.target.value)}
                                >
                                    <option value="" disabled>Odaberite kategoriju</option>
                                    <option value="firstName">Ime</option>
                                    <option value="lastName">Prezime</option>
                                    <option value="education">Strucna sprema</option>
                                    <option value="cvContent">Sadrzaj CV</option>
                                    <option value="clContent">Sadrzaj propratnog pisma</option>
                                </select>
                            </div>
                        </div>
                        {category!="education" && category != "" && <div className="row mt-3">
                            <div className="col-md-12">
                                <label className="labels">Tekst</label>
                                <input type="text" className="form-control" placeholder="..." value={text} onChange={(e) => handleSetText(e.target.value)} />
                            </div>
                        </div>}
                        {category=="education" && category != "" && <div className="row mt-3">
                            <div className="col-md-12">
                                <label className="labels">Stepen</label>
                                <select id="InputStepen"
                                    name="degree"
                                    className="form-control"
                                    value={degree}
                                    onChange={(e) => handleSetDegree(e.target.value)}
                                >
                                    <option value="" disabled>Odaberite stepen strucne spreme</option>
                                    <option value="I">I Stepen (cetiri razreda osnovne)</option>
                                    <option value="II">II Stepen (osnovna skola)</option>
                                    <option value="III">III Stepen (SSS srednja skola)</option>
                                    <option value="IV">IV Stepen (SSS srednja skola)</option>
                                    <option value="V">V Stepen (VKV - SSS srednja skola)</option>
                                    <option value="VI">VI Stepen (VSS visa skola)</option>
                                    <option value="VII">VII (Visoka strusna sprema)</option>
                                    <option value="VIII">VIII (Doktor nauka)</option>
                                </select>
                            </div>
                        </div>}
                        <div className="row mt-3">
                            <div className="col-md-12">
                                <div className="form-check">
                                    <input className="form-check-input" type="checkbox" value={phraseQuery} onChange={handleChangePhraseQuery} id="flexCheckDefault" />
                                    <label className="form-check-label">
                                    Phrase Query
                                    </label>
                                </div>
                            </div>
                        </div>
                        {conditionIndex!=numOfElements-1 && 
                        <div className="row mt-3">
                            <div className="col-md-12">
                                <label className="labels">Operator</label>
                                <select id="InputOperator"
                                    name="operator"
                                    className="form-control"
                                    value={operator}
                                    onChange={(e) => handleSetOperator(e.target.value)}
                                >
                                    <option value="" disabled>Odaberite logicki operator</option>
                                    <option value="AND">I</option>
                                    <option value="OR">ILI</option>
                                </select>
                            </div>
                        </div>}
                        </div>
                        <div className="col">
                        <div className="mt-5 text-center">
                            <button onClick={(e) => ukloniUslov(e)} disabled={removeDisabled()} className="btn btn-danger profile-button" type="button">Ukloni uslov</button>
                        </div>
                        <div className="mt-5 text-center">
                            <button onClick={(e) => dodajUslov(e)} disabled={Validate()} className="btn btn-primary profile-button" type="button">Dodaj uslov</button>
                        </div>
                        </div>
                    </div>
                </div>
            </div>         
        </div>
        </>
    );
}

export default AdvancedSearchCondition;