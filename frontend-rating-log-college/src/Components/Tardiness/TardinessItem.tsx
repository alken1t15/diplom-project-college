import React, {useEffect, useState} from 'react';

interface ITardinessItem{
    date: string;
    nameOfDay: string;
    tardiness: object[]
}
const TardinessItem: React.FC<ITardinessItem[]> = (props) => {
    let [item, setItem] = useState(props);

    useEffect(()=>{
        setItem(props)
        console.log(props)
    }, [props])

    return (
        <div className="tardiness-block">

        </div>
    );
};

export default TardinessItem;