import AdvancedSearchCondition from "./AdvancedSearchCondition";

const AdvancedSearchConditionList = ({conditions, numOfElements, addConditionChanger, updateConditionChanger, removeConditionChanger}) => {

    return (
        <div>
                        {conditions && conditions.map((condition, index) => {
                            return (
                                <AdvancedSearchCondition condition={condition} key={index} numOfElements={numOfElements} removeConditionChanger={removeConditionChanger}
                                addConditionChanger={addConditionChanger} updateConditionChanger={updateConditionChanger} conditionIndex={index}/>
                            )
                        })
                        }
        </div>
    );
}

export default AdvancedSearchConditionList;