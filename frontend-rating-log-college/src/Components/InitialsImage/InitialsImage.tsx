import React from 'react';
import {Stage, Layer, Text, Rect} from 'react-konva';
interface InitialsImageProps {
    initials: string;
    width: number;
    height: number;
    fontSize: number;
    textColor: string;
    backgroundColor: string;
}
const InitialsImage: React.FC<InitialsImageProps> = (props ) => {
    const { initials, width, height, fontSize, textColor, backgroundColor } = props;
    return (
        <Stage width={width} height={height}>
            <Layer back>
                <Rect width={width}
                      height={height}
                      fill={backgroundColor}
                      cornerRadius={50}
                />
                <Text
                    text={initials}
                    fontSize={fontSize}
                    fill={textColor}
                    width={width}
                    fontWeight="bold"
                    height={height}
                    align="center"
                    verticalAlign="middle"
                />
            </Layer>
        </Stage>
    );
};

export default InitialsImage;